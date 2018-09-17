package com.yzk.controller;

import com.yzk.exception.AccessException;
import com.yzk.model.Response;
import com.yzk.util.ResponseUtil;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/17
 *     desc   : 文件上传
 * </pre>
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController{

    /**
     * 单文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Response upload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()){
            try {
                _saveFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new AccessException(44003, "文件保存失败");
            } catch (IOException e) {
                e.printStackTrace();
                throw new AccessException(44002, "文件保存失败");
            }
            return ResponseUtil.success();
        }else {
            return ResponseUtil.error(44001, "文件为空，上传失败");
        }
    }

    /**
     * 多文件上传
     * @param request
     * @return
     */
    @PostMapping("/upload/batch")
    public Response uploadFiles(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        logger.info(files.size()+"");
        if (files.isEmpty()){
            return ResponseUtil.error(44013, "文件为空");
        }
        _saveFiles(files);
        return ResponseUtil.success();
    }


    /**
     * 同时上传文件和json
     * @param request
     * @return
     */
    @PostMapping("/upload/file_data")
    public Response uploadJsonAndFile(@RequestParam("json") JSONObject json, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (files.isEmpty()){
            return ResponseUtil.error(44013, "文件为空");
        }
        _saveFiles(files);
        logger.info("json: " + json.toString());
        return ResponseUtil.success(json.toString());
    }

    /**
     * 返回保存的文件
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    private File _createSaveFile(String filename) throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()){
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(), "static/upload/" + filename);
        return upload;
    }

    /**
     * 保存上传的单个文件
     * @param file
     * @throws IOException
     */
    private void _saveFile(MultipartFile file) throws IOException {
        File upload = _createSaveFile(file.getOriginalFilename());
        logger.info(upload.getAbsolutePath());
        if (!upload.getParentFile().exists()) {
            upload.getParentFile().mkdirs();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(upload));
        bos.write(file.getBytes());
        bos.flush();
        bos.close();
    }

    /**
     * 保存上传的多个文件
     * @param files
     */
    private void _saveFiles(List<MultipartFile> files){
        for(int i = 0; i < files.size(); i++){
            MultipartFile file = files.get(i);
            if (!file.isEmpty()){
                try {
                    _saveFile(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new AccessException(44015, "第"+i+"个文件保存失败");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new AccessException(44012, "第"+i+"个文件保存失败");
                }
            }else {
                throw new AccessException(44010,"第"+i+"个文件上传失败");
            }
        }
    }


}











