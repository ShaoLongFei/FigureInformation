package javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 幽蓝丶流月 on 2017/9/20.
 */

public class BookBean {


    /**
     * status : 0
     * msg : ok
     * result : {"title":"有理想就有疼痛","subtitle":"中国当代文化名人访谈录","pic":"http://api.jisuapi.com/isbn/upload/20161010/174050_28792.jpg","author":"高晓春","summary":"《有理想就有疼痛:中国当代文化名人访谈录》是一份关于当代中国文化的最真实底稿，收录了高晓春与白先勇、冯骥才、余华、莫言、余秋雨、陈忠实等20位当代中国文化大家的对话。通过近距离的访谈，展现了这些文化大家多彩的内心世界，也阐释了他们对生命、艺术、财富及文化的理解。","publisher":"","pubplace":"","pubdate":"2013-1","page":"256","price":"29.00","binding":"","isbn":"9787212058937","isbn10":"7212058939","keyword":"","edition":"","impression":"","language":"","format":"","class":""}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * title : 有理想就有疼痛
         * subtitle : 中国当代文化名人访谈录
         * pic : http://api.jisuapi.com/isbn/upload/20161010/174050_28792.jpg
         * author : 高晓春
         * summary : 《有理想就有疼痛:中国当代文化名人访谈录》是一份关于当代中国文化的最真实底稿，收录了高晓春与白先勇、冯骥才、余华、莫言、余秋雨、陈忠实等20位当代中国文化大家的对话。通过近距离的访谈，展现了这些文化大家多彩的内心世界，也阐释了他们对生命、艺术、财富及文化的理解。
         * publisher :
         * pubplace :
         * pubdate : 2013-1
         * page : 256
         * price : 29.00
         * binding :
         * isbn : 9787212058937
         * isbn10 : 7212058939
         * keyword :
         * edition :
         * impression :
         * language :
         * format :
         * class :
         */

        private String title;
        private String subtitle;
        private String pic;
        private String author;
        private String summary;
        private String publisher;
        private String pubplace;
        private String pubdate;
        private String page;
        private String price;
        private String binding;
        private String isbn;
        private String isbn10;
        private String keyword;
        private String edition;
        private String impression;
        private String language;
        private String format;
        @SerializedName("class")
        private String classX;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getPubplace() {
            return pubplace;
        }

        public void setPubplace(String pubplace) {
            this.pubplace = pubplace;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBinding() {
            return binding;
        }

        public void setBinding(String binding) {
            this.binding = binding;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getIsbn10() {
            return isbn10;
        }

        public void setIsbn10(String isbn10) {
            this.isbn10 = isbn10;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getEdition() {
            return edition;
        }

        public void setEdition(String edition) {
            this.edition = edition;
        }

        public String getImpression() {
            return impression;
        }

        public void setImpression(String impression) {
            this.impression = impression;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }
    }
}
