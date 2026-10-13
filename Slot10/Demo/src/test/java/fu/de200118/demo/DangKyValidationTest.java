package fu.de200118.demo;

import fu.de200118.demo.controller.DangKyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DangKyValidationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testShowFormDefaultLocaleVi() throws Exception {
        mockMvc.perform(get("/dangky"))
                .andExpect(status().isOk())
                .andExpect(view().name("dangky/form"))
                .andExpect(model().attributeExists("nguoiDung"));
    }

    @Test
    void testSubmitEmptyFormHasErrors() throws Exception {
        mockMvc.perform(post("/dangky")
                        .locale(Locale.forLanguageTag("vi")))
                .andExpect(status().isOk())
                .andExpect(view().name("dangky/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("nguoiDung", "hoTen", "email", "tuoi"));
    }

    @Test
    void testSubmitInvalidValues() throws Exception {
        mockMvc.perform(post("/dangky")
                        .param("hoTen", "A")
                        .param("email", "invalid-email")
                        .param("tuoi", "17")
                        .param("soDienThoai", "123")
                        .locale(Locale.forLanguageTag("vi")))
                .andExpect(status().isOk())
                .andExpect(view().name("dangky/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("nguoiDung", "hoTen", "email", "tuoi", "soDienThoai"));
    }

    @Test
    void testSubmitValidFormRedirects() throws Exception {
        mockMvc.perform(post("/dangky")
                        .param("hoTen", "Nguyen Van An")
                        .param("email", "an@example.com")
                        .param("tuoi", "20")
                        .param("soDienThoai", "0901234567"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dangky/thanh-cong"))
                .andExpect(flash().attribute("hoTen", "Nguyen Van An"));
    }

    @Test
    void testSuccessPage() throws Exception {
        mockMvc.perform(get("/dangky/thanh-cong")
                        .flashAttr("hoTen", "Nguyen Van An"))
                .andExpect(status().isOk())
                .andExpect(view().name("dangky/thanh-cong"));
    }
}
