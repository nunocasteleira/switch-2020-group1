package switchtwentytwenty.project;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.repositories.RoleRepository;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    Main main = new Main();

    @Test
    void ensureItsWorking(){
        RoleRepository roleRepository = new RoleRepository();

        assertNotNull(main);
        assertNotNull(main.setUpRoles(roleRepository));
        assertNotNull(main.corsConfigurer());
    }
/*

    @Test
    void main() {
        Main.main(new String[] {});
    }

}*/
}