package fu.de200118.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrangChuController {
    @GetMapping("/")
    public String trangChu() {
        return "trang-chu";
    }
}