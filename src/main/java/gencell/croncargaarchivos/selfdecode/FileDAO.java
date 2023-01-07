package gencell.croncargaarchivos.selfdecode;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class FileDAO implements Serializable{
    
    @JsonProperty("profile_id")
    private String profile_id;
    
    @JsonProperty("upload_provider")
    private String upload_provider;
    
    @JsonProperty("genome_file")
    private String genome_file;
    
    @JsonProperty("filename")
    private String filename;
    
    @JsonProperty("filename_reverse")
    private String filename_reverse;
    
    @JsonProperty("file_id")
    private String file_id;
    
    @JsonProperty("forward_reads")
    private String forward_reads;
    
    @JsonProperty("reverse_reads")
    private String reverse_reads;

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getUpload_provider() {
        return upload_provider;
    }

    public void setUpload_provider(String upload_provider) {
        this.upload_provider = upload_provider;
    }

    public String getGenome_file() {
        return genome_file;
    }

    public void setGenome_file(String genome_file) {
        this.genome_file = genome_file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename_reverse() {
        return filename_reverse;
    }

    public void setFilename_reverse(String filename_reverse) {
        this.filename_reverse = filename_reverse;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getForward_reads() {
        return forward_reads;
    }

    public void setForward_reads(String forward_reads) {
        this.forward_reads = forward_reads;
    }

    public String getReverse_reads() {
        return reverse_reads;
    }

    public void setReverse_reads(String reverse_reads) {
        this.reverse_reads = reverse_reads;
    }
    
    
    
}
