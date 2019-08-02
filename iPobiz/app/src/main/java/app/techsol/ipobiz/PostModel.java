package app.techsol.ipobiz;

class PostModel {
    String posttitle, postimg;

    public PostModel() {
    }

    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public String getPostimg() {
        return postimg;
    }

    public void setPostimg(String postimg) {
        this.postimg = postimg;
    }

    public PostModel(String posttitle, String postimg) {
        this.posttitle = posttitle;
        this.postimg = postimg;
    }
}
