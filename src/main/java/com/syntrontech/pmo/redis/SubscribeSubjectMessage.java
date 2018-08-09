package com.syntrontech.pmo.redis;

import com.syntrontech.pmo.service.PmoUserService;
import com.syntrontech.pmo.util.SpringContextHelperImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syntrontech.autoTool.util.SpringContextHelper;

public class SubscribeSubjectMessage implements SubscribeMessageHandler{

	private static final Logger logger = LoggerFactory.getLogger(SubscribeSubjectMessage.class);
	
	private SpringContextHelper helper = SpringContextHelperImp.getInstance();

	private PmoUserService getSubjectService(){
		return helper.getBean(PmoUserService.class);
	}

	@Override
	public void handleUpdateAction(String id) {
		
//		getOwnershipService().updateOwnerShip(id);
		getSubjectService().saveUser(id);
		
	}

	@Override
	public void handleDeleteAction(String id) {

		
	}

//	private void deleteOwnerships(String id) {
//
//		try {
//			List<String> myOwnerships = getOwnershipService().findOwnerShipsByMyId(id);
//			List<String> userOwnerships = getOwnershipService().findOwnerShipsByUserId(id);
//
//			getOwnershipService().cancleFollow(id, myOwnerships);
//			getOwnershipService().deleteFollow(id, userOwnerships);
//
//		} catch (SolrException e) {
//			logger.debug("user : [{}] search from Solr error get message =>", id, e.getMessage());
//		} catch (InternalServiceException e) {
//			logger.debug("user : [{}] search from Solr error get message =>", id, e.getMessage());
//		} catch (ServerException e) {
//			logger.debug("user : [{}] delete Ownership error get message =>", id, e.getMessage());
//		} catch (ClientException e) {
//			logger.debug("user : [{}] delet on friend service error get message =>", id, e.getMessage());
//		} catch (ForbiddenException e) {
//			logger.debug("user : [{}] delet on friend service error  error message =>", id, e.getMessage());
//		} catch (NotFoundException e) {
//			logger.debug("user : [{}] delet on friend service error  error message =>", id, e.getMessage());
//		}
//
//	}

}
