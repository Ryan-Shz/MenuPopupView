package com.sc.sample;

/**
 * @author ShamChu
 * @Date 17/9/6 21:52
 */
public class TestMenuBean {

    private String menuText;

    TestMenuBean(String menuText) {
        this.menuText = menuText;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }
}
