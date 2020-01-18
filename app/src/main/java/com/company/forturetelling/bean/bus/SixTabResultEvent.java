package com.company.forturetelling.bean.bus;

import java.util.ArrayList;

/**
 * Created by Lovelin on 2019/12/24
 * <p>
 * Describe:
 */
public class SixTabResultEvent {
    private String username;
    private String birthday;
    private String born;
    private String zodiac;//生肖
    private ArrayList<String> mDataList;
    private CharacterBean CharacterBean;
    private LoveBean LoveBean;
    private BusinessBean BusinessBean;
    private RichBean RichBean;
    private HealthBean HealthBean;

    public SixTabResultEvent(String username, String birthday, String born, String zodiac, ArrayList<String> mDataList,
                             SixTabResultEvent.CharacterBean characterBean, SixTabResultEvent.LoveBean loveBean,
                             SixTabResultEvent.BusinessBean businessBean, SixTabResultEvent.RichBean richBean,
                             SixTabResultEvent.HealthBean healthBean) {
        this.username = username;
        this.birthday = birthday;
        this.born = born;
        this.zodiac = zodiac;
        this.mDataList = mDataList;
        CharacterBean = characterBean;
        LoveBean = loveBean;
        BusinessBean = businessBean;
        RichBean = richBean;
        HealthBean = healthBean;
    }
//性格-爱情-事业-财运-健康


    public static class CharacterBean {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class LoveBean {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class BusinessBean {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class RichBean {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class HealthBean {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    @Override
    public String toString() {
        return "SixTabResultEvent{" +
                "username='" + username + '\'' +
                ", birthday='" + birthday + '\'' +
                ", born='" + born + '\'' +
                ", zodiac='" + zodiac + '\'' +
                ", mDataList=" + mDataList +
                ", CharacterBean=" + CharacterBean +
                ", LoveBean=" + LoveBean +
                ", BusinessBean=" + BusinessBean +
                ", RichBean=" + RichBean +
                ", HealthBean=" + HealthBean +
                '}';
    }
}
