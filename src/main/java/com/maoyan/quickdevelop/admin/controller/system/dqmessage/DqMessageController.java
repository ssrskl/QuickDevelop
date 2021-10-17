package com.maoyan.quickdevelop.admin.controller.system.dqmessage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.maoyan.quickdevelop.admin.controller.system.BaseController;
import com.maoyan.quickdevelop.common.core.AjaxResult;
import com.maoyan.quickdevelop.common.core.domain.DqMessage;
import com.maoyan.quickdevelop.system.service.IDqMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dqmessage")
public class DqMessageController extends BaseController {
  @Autowired
  private IDqMessageService iDqMessageService;

  @SaCheckLogin
  @GetMapping("/mymessage")
  public AjaxResult selectNowDqUserDqMessages(@RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
                                              @RequestParam(defaultValue = "10", name = "pageSize") int pageSize){
    List<DqMessage> dqMessages = iDqMessageService.selectNowDqUserDqMessages(pageNum, pageSize);
    return AjaxResult.success("查询成功",dqMessages);
  }

  @SaCheckLogin
  @GetMapping("/isread/{messageId}")
  public AjaxResult updateNowDqUserMessageIsReadById(@PathVariable Long messageId){
    Integer integer = iDqMessageService.updateNowDqUserMessageIsReadById(messageId);
    return AjaxResult.success("修改成功",integer);
  }

  /** 不应该手动发送，应该在AOP中自动发送 **/

}
