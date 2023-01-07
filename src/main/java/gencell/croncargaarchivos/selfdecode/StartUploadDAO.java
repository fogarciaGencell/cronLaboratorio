package gencell.croncargaarchivos.selfdecode;


public class StartUploadDAO {
    
    private String id;
    private String genomeFile;
    private String uploadUrl;
    private String expiresAt;
    private String url1;
    private String url2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenomeFile() {
        return genomeFile;
    }

    public void setGenomeFile(String genomeFile) {
        this.genomeFile = genomeFile;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }
    
    
    
}

