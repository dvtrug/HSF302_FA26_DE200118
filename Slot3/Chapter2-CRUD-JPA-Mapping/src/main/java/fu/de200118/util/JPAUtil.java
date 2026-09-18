package fu.de200118.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("HSF302_Chapter1");

    private JPAUtil() {
    }

    public static EntityManagerFactory getEMF() {
        return EMF;
    }

    public static void closeEMF() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return null;
    }
}
