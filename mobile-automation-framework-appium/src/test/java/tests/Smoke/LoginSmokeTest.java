package tests.Smoke;

import framework.screens.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

public class LoginSmokeTest extends BaseTest {

    @Test
    public void loginValidateHomeAndLogout() {

        HomeAppScreen homeApp = new HomeAppScreen();
        MenuScreen menu = new MenuScreen();
        LoginScreen login = new LoginScreen();
        ProductHomeScreen products = new ProductHomeScreen();
        LogoutConfirmDialog logoutDialog = new LogoutConfirmDialog();

        homeApp.openMenu();
        menu.goToLogin();
        login.login("bod@example.com", "10203040");

        Assert.assertTrue(products.isLoaded(),
                "No se cargó la pantalla principal (Products) después del login.");

        // Reintento porque a veces el menú no abre al primer intento tras el login
        try {
            homeApp.openMenu();
        } catch (Exception e) {
            homeApp.openMenu();
        }

        menu.logout();
        logoutDialog.confirm();
    }
}
