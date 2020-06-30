package jp.mirageworld.spring.bbs.validator.group;

import javax.validation.GroupSequence;

/**
 * 登録・更新共通
 */
@GroupSequence({
		Create.class,
		Update.class
})
public interface Save {

}
