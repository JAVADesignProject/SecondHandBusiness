package sever.handler.info;

import base.KClass;
import base.Message;
import base.Parser;
import base.json.CommentJson;
import base.json.MessageJson;
import base.json.ProductionJson;
import base.json.UserJson;
import sever.api.KComment;
import sever.api.KMessage;
import sever.api.KProduction;
import sever.api.KUser;
import sever.base.MessageHandler;

public class InfoMessageHandler extends MessageHandler {

    private int token = Integer.MIN_VALUE;

    public void logout() {
        KUser.logout (token);
    }

    @Override
    public Message handleMessage(Message message) {
        Message result = null;
        switch (message.code) {
            case KClass.LOGIN:
                result = KUser.login (UserJson.parse (message.props));
                token = result.token;
                break;
            case KClass.REGISTER:
                result = KUser.register (UserJson.parse (message.props));
                break;
            case KClass.UPDATE_PASSWORD:
                result = KUser.changePassword(UserJson.parse (message.props));
                break;
            case KClass.ADD_PRODUCTION:
                result = KProduction.addProduction (ProductionJson.parse (message.props));
                break;
            case KClass.DELETE_PRODUCTION:
                result = KProduction.deleteProduction (ProductionJson.parse (message.props));
                break;
            case KClass.NORMAL_PRODUCTION_BUY:
                result = KProduction.buyNormalProduction (ProductionJson.parse (message.props));
                break;
            case KClass.AUCTION_PRODUCTION_BUY:
                result = KProduction.buyAuctionProduction (ProductionJson.parse (message.props));
                break;
            case KClass.USER_INFO:
                var user = KUser.getUserInfo (UserJson.parse (message.props).userID);
                result = new Message (0, 0, user == null ? "" : user.toString ( ));
                break;
            case KClass.PRODUCTION_INFO:
                var production = KProduction.getProductionInfo (ProductionJson.parse (message.props).production_id);
                result = new Message (0, 0, production == null ?"":production.toString ());
                break;
            case KClass.CHAT_MSG:
                result = KMessage.getMessage(token, MessageJson.parse(message.props));
                break;
            case KClass.SEARCH:
                result = new Message (0,0,Parser.toJson (KProduction.search (message.props)));
                break;
            case KClass.MY_PRODUCTION:
                result = new Message(0,0,Parser.toJson (KProduction.getMyProduction (UserJson.parse (message.props).userID)));
                break;
            case KClass.ADD_COMMENT:
                result  = KComment.addComment (CommentJson.parse (message.props));
                break;
            case KClass.GET_COMMENT:
                result = new Message (0,0,Parser.toJson (KComment.getComment (ProductionJson.parse (message.props).production_id)));
                break;
            case KClass.MY_CHAT:
                result = KMessage.getNewChat(UserJson.parse (message.props));
                break;

        }
        return result;
    }
}
