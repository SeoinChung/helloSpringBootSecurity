package kr.ac.hansung.cse.hellospringbootsecurity.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.hellospringbootsecurity.entity.Product;
import kr.ac.hansung.cse.hellospringbootsecurity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping({"", "/"}) // products 또는 /products/ 둘 다 매핑
    public String viewHomePage(Model model, Principal principal) { // principal은 현재 로그인한 사용자의 정보를 담고 있는 객체
        String email = principal.getName(); // 로그인한 사용자의 이메일
        model.addAttribute("userEmail", email); // 모델에 주입해서 html에서 사용할 수 있도록 함

        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);

        return "index";
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {

        Product product = new Product();
        model.addAttribute("product", product);

        return "new_product";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductPage(@PathVariable(name = "id") Long id, Model model) {

        Product product = service.get(id);
        model.addAttribute("product", product);

        return "edit_product";
    }

    //  JSON 데이터(예: {"name": "Laptop", "brand": "Samsung", "madeIn": "Korea", "price": 1000.00})를 Product 객체에 매핑
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult,
                              Model model) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            // 기존 상품인 경우
            if (product.getId() != null) {
                return "edit_product"; // 수정 페이지로
            }
            else {
                return "new_product";  // 등록 페이지로
            }
        }

        service.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {

        service.delete(id);
        return "redirect:/products";
    }
}
