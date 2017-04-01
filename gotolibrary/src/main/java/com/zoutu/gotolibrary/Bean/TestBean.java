package com.zoutu.gotolibrary.Bean;

import java.util.List;

/**
 * Created by admin on 2017/3/30.
 */

public class TestBean {


    /**
     * platform : all
     * audience : {"alias":["ba4dfdc1271a49df86d0061ac69f6755"]}
     * notification : {"alert":"请接单","android":{"alert":"请接单"},"ios":{"alert":"请接单","badge":"1","sound":"default","category":"www.gotogoto.com"}}
     * message : {"msg_content":"www.gotogoto.com"}
     * options : {"sendno":1507017860,"apns_production":false}
     */

    private String platform;
    private AudienceBean audience;
    private NotificationBean notification;
    private MessageBean message;
    private OptionsBean options;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public AudienceBean getAudience() {
        return audience;
    }

    public void setAudience(AudienceBean audience) {
        this.audience = audience;
    }

    public NotificationBean getNotification() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        this.notification = notification;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public OptionsBean getOptions() {
        return options;
    }

    public void setOptions(OptionsBean options) {
        this.options = options;
    }

    public static class AudienceBean {
        private List<String> alias;

        public List<String> getAlias() {
            return alias;
        }

        public void setAlias(List<String> alias) {
            this.alias = alias;
        }
    }

    public static class NotificationBean {
        /**
         * alert : 请接单
         * android : {"alert":"请接单"}
         * ios : {"alert":"请接单","badge":"1","sound":"default","category":"www.gotogoto.com"}
         */

        private String alert;
        private AndroidBean android;
        private IosBean ios;

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public AndroidBean getAndroid() {
            return android;
        }

        public void setAndroid(AndroidBean android) {
            this.android = android;
        }

        public IosBean getIos() {
            return ios;
        }

        public void setIos(IosBean ios) {
            this.ios = ios;
        }

        public static class AndroidBean {
            /**
             * alert : 请接单
             */

            private String alert;

            public String getAlert() {
                return alert;
            }

            public void setAlert(String alert) {
                this.alert = alert;
            }
        }

        public static class IosBean {
            /**
             * alert : 请接单
             * badge : 1
             * sound : default
             * category : www.gotogoto.com
             */

            private String alert;
            private String badge;
            private String sound;
            private String category;

            public String getAlert() {
                return alert;
            }

            public void setAlert(String alert) {
                this.alert = alert;
            }

            public String getBadge() {
                return badge;
            }

            public void setBadge(String badge) {
                this.badge = badge;
            }

            public String getSound() {
                return sound;
            }

            public void setSound(String sound) {
                this.sound = sound;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }
        }
    }

    public static class MessageBean {
        /**
         * msg_content : www.gotogoto.com
         */

        private String msg_content;

        public String getMsg_content() {
            return msg_content;
        }

        public void setMsg_content(String msg_content) {
            this.msg_content = msg_content;
        }
    }

    public static class OptionsBean {
        /**
         * sendno : 1507017860
         * apns_production : false
         */

        private int sendno;
        private boolean apns_production;

        public int getSendno() {
            return sendno;
        }

        public void setSendno(int sendno) {
            this.sendno = sendno;
        }

        public boolean isApns_production() {
            return apns_production;
        }

        public void setApns_production(boolean apns_production) {
            this.apns_production = apns_production;
        }
    }
}
