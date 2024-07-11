//package com.cool.take_out_admin.controller;
//
//
//
///**
// * 通用接口
// */
//@RestController
//@RequestMapping("/admin/common")
//@Api(tags = "通用接口")
//@Slf4j
//public class CommonController {
//
//    @Resource
//    private AliOssUtil aliOssUtil;
//
//    /**
//     * 文件上传
//     *
//     * @param file
//     * @return
//     */
//    @PostMapping("/upload")
//    @ApiOperation("文件上传")
//    public Result<String> upload(MultipartFile file) {
//        log.info("文件上传：{}", file);
//        try {
//            //原始文件名
//            String originalFilename = file.getOriginalFilename();
//            //截取原始文件名的后缀   dfdfdf.png
//            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            //构造新文件名称
//            String objectName = UUID.randomUUID().toString() + extension;
//
//            //文件的请求路径
//            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
//            return Result.success(filePath);
//        } catch (IOException e) {
//            log.error("文件上传失败：{}", e);
//        }
//        return Result.error(MessageConstant.UPLOAD_FAILED);
//    }
//}
//
