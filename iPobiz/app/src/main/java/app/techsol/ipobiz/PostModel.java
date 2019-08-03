package app.techsol.ipobiz;

public class PostModel {
    public PostModel() {
    }

    String posttitle, postimg, postbody;

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

    public String getPostbody() {
        return postbody;
    }

    public void setPostbody(String postbody) {
        this.postbody = postbody;
    }

    public PostModel(String posttitle, String postimg, String postbody) {
        this.posttitle = posttitle;
        this.postimg = postimg;
        this.postbody = postbody;
    }
}
