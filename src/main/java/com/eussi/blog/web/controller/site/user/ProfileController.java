package com.eussi.blog.web.controller.site.user;

import com.eussi.blog.base.data.Data;
import com.eussi.blog.base.lang.Consts;
import com.eussi.blog.modules.service.SecurityCodeService;
import com.eussi.blog.modules.service.UserService;
import com.eussi.blog.modules.vo.AccountProfile;
import com.eussi.blog.modules.vo.UserVO;
import com.eussi.blog.web.controller.BaseController;
import com.eussi.blog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangxm
 *
 */
@Controller
@RequestMapping("/user")
public class ProfileController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityCodeService securityCodeService;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String view(ModelMap model) {
		AccountProfile profile = getProfile();
		UserVO view = userService.get(profile.getId());
		model.put("view", view);
		return view(Views.USER_PROFILE);
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String post(String name, String signature, ModelMap model) {
		Data data;
		AccountProfile profile = getProfile();
		try {
			UserVO user = new UserVO();
			user.setId(profile.getId());
			user.setName(name);//昵称不做重复限制
			user.setSignature(signature);

            //更新profile
			putProfile(userService.update(user));

			// put 最新信息
			UserVO view = userService.get(profile.getId());
			model.put("view", view);

			data = Data.success("操作成功", Data.NOOP);
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return view(Views.USER_PROFILE);
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public String email() {
		return view(Views.USER_EMAIL);
	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public String emailPost(String email, String code, ModelMap model) {
		Data data;
		AccountProfile profile = getProfile();
		try {
			Assert.hasLength(email, "请输入邮箱地址");
			Assert.hasLength(code, "请输入验证码");

			securityCodeService.verify(profile.getId(), Consts.VERIFY_BIND, code);
			// 先执行修改，判断邮箱是否更改，或邮箱是否被人使用
			AccountProfile p = userService.updateEmail(profile.getId(), email);
			putProfile(p);

			data = Data.success("操作成功", Data.NOOP);
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return view(Views.USER_EMAIL);
	}

}
