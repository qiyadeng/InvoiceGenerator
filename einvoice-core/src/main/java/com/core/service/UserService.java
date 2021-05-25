package com.core.service;


import com.core.bean.User;
import com.core.dao.EinvoiceHeadDao;
import com.core.dao.IUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserDao userDao;

	public User getByMerchant_code(String merchant_code){
		return userDao.getByMerchant_code(merchant_code);
	}

	public User merchantLogin(String appId,String appSecret) throws UnsupportedEncodingException {
		User user = userDao.findByAppid(appId);
		if (null == user) {
			return null;
		}else {
			String secret =  user.getAppSecret();
			if (secret.equals(appSecret)) {
				return user;
			}
		}
		return null;
	}
}
