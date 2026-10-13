package fu.de200118.demo;

import fu.de200118.demo.model.KhoaHoc;
import fu.de200118.demo.service.KhoaHocService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class KhoaHocCrudTest {

    @Autowired
    private KhoaHocService khoaHocService;

    @Test
    void testCrudOperations() {
        // 1. CREATE
        KhoaHoc kh = new KhoaHoc("Thymeleaf nâng cao", "Phạm D", 2);
        KhoaHoc saved = khoaHocService.save(kh);
        assertNotNull(saved.getId());

        // 2. READ & SEARCH
        List<KhoaHoc> searchList = khoaHocService.search("Thymeleaf");
        assertFalse(searchList.isEmpty());
        assertTrue(searchList.stream().anyMatch(k -> k.getTenKhoaHoc().equals("Thymeleaf nâng cao")));

        // 3. UPDATE
        saved.setSoTinChi(4);
        KhoaHoc updated = khoaHocService.save(saved);
        assertEquals(4, updated.getSoTinChi());

        // 4. FIND NON-EXISTENT
        Optional<KhoaHoc> notFound = khoaHocService.findById(999999L);
        assertTrue(notFound.isEmpty());

        // 5. DELETE
        boolean deleted = khoaHocService.delete(saved.getId());
        assertTrue(deleted);
        assertTrue(khoaHocService.findById(saved.getId()).isEmpty());
    }
}
