package com.maoyan.quickdevelop.admin.controller.system;

import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.utils.UploadUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 猫颜
 * @date 2021/6/2 19:55
 * 文件上传工具类(上传时参数key为file)！！！！
 */
@RestController
@RequestMapping("/upload/")
public class UploadController {

    /**
     * 上传图片
     */
    @PostMapping("image")
    @ApiOperation(value = "图片上传")
    public AjaxResult image(MultipartFile file) {
        //System.out.println("文件名："+file.getOriginalFilename());
        // 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
        UploadUtil.checkFileSize(file);
        UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.imageSuffix);
        String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.imageFolder);
        return AjaxResult.success(httpUrl);
    }

    /**
     * 上传视频
     */
    @PostMapping("video")
    @ApiOperation(value = "视频上传")
    public AjaxResult video(MultipartFile file) {
        // 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
        UploadUtil.checkFileSize(file);
        UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.videoSuffix);
        String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.videoFolder);
        return AjaxResult.success(httpUrl);
    }

    /**
     * 上传音频
     */
    @PostMapping("audio")
    @ApiOperation(value = "音频上传")
    public AjaxResult audio(MultipartFile file) {
        // 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
        UploadUtil.checkFileSize(file);
        UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.audioSuffix);
        String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.audioFolder);
        return AjaxResult.success(httpUrl);
    }

    /**
     * 上传apk
     */
    @PostMapping("apk")
    @ApiOperation(value = "安卓应用程序上传")
    public AjaxResult apk(MultipartFile file) {
        // 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
        UploadUtil.checkFileSize(file);
        UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.apkSuffix);
        String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.apkFolder);
        return AjaxResult.success(httpUrl);
    }

    /**
     * 上传任意文件
     */
    @PostMapping("file")
    @ApiOperation(value = "任意文件上传")
    public AjaxResult file(MultipartFile file) {
        // 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
        UploadUtil.checkFileSize(file);
        UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.fileSuffix);
        String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.fileFolder);
        return AjaxResult.success(httpUrl);
    }
}
