package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @RequestMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Optional<Product> productOptional = this.productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
        }
        return "/admin/product/detail";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping(value = "/admin/product/create")
    public String createProductPage(Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(" >>>> " + error.getField() + " - " + error.getDefaultMessage());
        }
        // validate
        if (bindingResult.hasErrors()) {
            return "/admin/product/create";
        }
        String image = this.uploadService.handleSaveUploadFile(file, "product");

        product.setImage(image);
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> productOptional = this.productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("newProduct", product);
        }
        return "/admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Optional<Product> productOptional = this.productService.getProductById(product.getId());
        if (productOptional.isPresent()) {
            Product currentProduct = productOptional.get();
            if (currentProduct != null) {
                // update new image
                if (!file.isEmpty()) {
                    String img = this.uploadService.handleSaveUploadFile(file, "product");
                    currentProduct.setImage(img);
                }

                currentProduct.setName(product.getName());
                currentProduct.setPrice(product.getPrice());
                currentProduct.setQuantity(product.getQuantity());
                currentProduct.setDetailDesc(product.getDetailDesc());
                currentProduct.setShortDesc(product.getShortDesc());
                currentProduct.setFactory(product.getFactory());
                currentProduct.setTarget(product.getTarget());

                this.productService.handleSaveProduct(currentProduct);
            }
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        Optional<Product> productOptional = this.productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
        }
        return "/admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }
}
