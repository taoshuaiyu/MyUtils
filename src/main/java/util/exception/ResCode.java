package util.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description Api 返回代码及解释.
 * @author plz
 */
public enum ResCode {

    OK(200, "成功"),
    INVALID_ARGS(400, "请求参数有误"),
    INVALID_ACCESS(401, "需要登录后访问"),
    LOGIN_TIME_OUT(402, "登录超时，请重新登录"),
    EXPIRED_SESSION(403, "您已在其他设备上登录"),
    API_NOT_FOUND(404, "找不到对应的api"),
    INVALID_JSON_FORMAT(405, "不是一个json格式"),
    AUTO_lOGIN_FAILED(406, "自动登录失败"),
    SERVER_ERROR(500, "服务器发生未知错误"),
    VERSION_ERROR(800, "版本检查失败"),
    /**
     * 手机号已注册
     */
    REGISTED_PHONE(641, "该手机号已被使用，请核实用户手机号，有问题请联系15167145350"),
    REGISTED_USER(642, "用户已注册"),
    ERROR_VALIDATION_CODE(643, "验证码失效或者输入不正确"),
    USER_NOT_FOUND(645, "用户不存在"),
    USER_HAS_FOUND(1645, "用户已存在于该机构"),
    ERROR_PASSWORD(646, "密码错误"),
    ERROR_RESOURCE(647, "来源错误，如需接入，请联系医链获取"),
    REGISTED_IDENTIFYID(648, "身份证已注册"),
    PHONE_VALIDATE_FALL(649, "手机号码格式不正确"),
    IDENTIFYID_VALIDATE_FALL(650, "身份证格式不正确"),
    HOSPITAL_MISMATCH(651, "请核实该用户医院、科室、职称信息，如有问题请联系客服15167145350"),
    PHONE_IS_NONE(652, "手机号不能为空"),
    USER_ADD_FAIL(653, "用户添加失败"),
    USER_UPDATE_FAIL(654, "用户更新失败"),
    USER_DELETE_FAIL(655, "用户删除失败"),
    USER_IS_TEACHING(656, "该用户的课程正在授课中,不可删除!"),
    USER_HAS_ASSOCIATE_COURSE1(657, "该用户关联的"),
    USER_HAS_ASSOCIATE_COURSE2(657, "门授课课程将被同步删除,确认删除用户?"),
    HOSPITAL_MISMATCH2(658, "请核实您的医院信息，如有问题，请联系15167145350"),
    MESSAGE_ERROR(712, "短信发送失败"),
    FAIL_LOGOUT(713, "注销失败"),
    USER_ENTER_SUCESS(720, "您已成功入驻，请登录使用云学院功能"),
    USER_ENTER_CHECK(721, "您已申请入驻，我们正在审核您的信息，请耐心等待"),
    INSTITUTION_EXISTING(722, "该机构名称已经被使用，请换一个名称再试"),
    ACCOUNT_FROZEN(10601, "您的账号已被冻结"),
    NO_BINDING_PHONE(10602, "您还没有绑定手机号码"),
    NO_IDENTIFICATION(10603, "需要认证通过后访问"),
    INVALID_PHONE(10701, "无效的手机号码"),
    INVALID_VISIT_CODE(10702, "无效的邀请码"),
    INSUFFICIENT_INFO(12001, "用户信息不足"),
    REMOTE_LOGIN_EXCEPTION(12402, "远程登陆异常"),
    REMOTE_PHONE_VALIDATION_ERROR(12403, "远程手机号码校验失败"),
    REMOTE_PHONE_NOT_MATCH(12404, "远程手机号码和您现在的手机号码不匹配"),
    DUPLICATED_REMOTE_BINDING(12405, "重复的帐号绑定"),
    UPLOAD_FILE_FAIL(12501, "上传文件保存失败，请重新提交"),
    UPLOAD_FILE_TYPE_ERROR(12502, "不支持该文件类型"),
    ILLEGAL_CHARACTER(12601, "存在非法字符"),
    WORD_NUMBER_OUT_OF_LIMIT(12602, "字数超出限制"),
    GET_USER_CENTER_TOKEN_ERROR(12701, "获取用户中心token错误"),
    REQUEST_OTHER_API_ERROR(12702, "请求第三方接口数据失败"),
    PHONE_HAS_USED(12703, "手机号已经存在"),


    /** 获取视频地址出错 */
    ERROR_GET_TENCENT_CLOUD_VIDEO_URL(12801, "没有找到相关视频"),

    FILE_ALREADY_EXIST(13001, "文件已存在，请重命名再上传"),
    NUMBER_LIMIT(13002, "考试题数不能大于总题数"),
    NUMBER_LIMIT2(13003, "合格题数不能大于考试题数"),

    ADD_COURSE_STUDENT(13002, "发布失败，必修课程请添加听课对象"),
    EXAM_NOT_UPLOAD(13011, "修改成功，考题暂未上传"),
    EVAL_NOT_UPLOAD(13012, "修改成功，测评暂未上传"),
    EXAM_EVAL_NOT_UPLOAD(13013, "修改成功，考题和测评暂未上传"),
    EXAM_NOT_RIGHT(13014, "修改失败，单位考题项需满足至少两个项内容，请校验"),
    EVAL_NOT_RIGHT_SINGLE(13015, "修改失败，单位测评项需满足至少两个项内容，请校验"),
    EVAL_NOT_RIGHT_MANY(13016, "修改失败，请完善测评内容"),
    EVAL_NOT_FOUND(13017,"未查询到测评信息"),

    /** 课程相关 13100~13149 */
    COURSE_NOT_EXIST(13100, "该课程不存在"),
    COURSEWARE_NOT_EXIST(13101, "课件信息不存在"),
    APPLY_LEAVE_FAILED(13102, "请假失败，请再试一次"),
    ONLINE_LEAVE_INVALID(13103, "在线课程无法请假"),
    LEAVE_RECORD_EXIST(13104, "您已经申请请假"),
    LEAVE_REASON_OVER_LENGTH(13105, "请假理由不能超过50个字符"),
    MARK_COURSEWARE_FAILED(13106, "收藏课件失败，请重新收藏"),
    FIND_COURSEWARE_FAILED(13107, "找不到课件信息"),
    COURSE_NOT_SELECTED(13108, "您没有报名该课程"),
    COURSE_COURSEWARE_NOT_MATCHED(13109, "课件不属于该课程"),
    SAVE_WATCH_RECORD_FAILD(13110, "保存观看进度失败"),
    NOT_ONLINECOURSE_SIGN_IN(13111, "非面授课程不需要签到"),
    ALREADY_SIGN_IN(13112, "已经签到"),
    SAVE_SIGN_IN_FAILED(13113, "保存签到信息失败"),
    SIGN_IN_OVER_TIME(13114, "课程已结束,无法签到"),
    FAILED_GET_RECOMMENDATION_COURSES(13115, "获取推荐课程失败"),
    COURSE_IS_START(13116, "授课已开始，课程相关信息不可更改"),
    COURSE_IS_END(13117, "对不起，课程已经结束"),
    COURSE_UPDATESTATUS_ON(13118, "课程发布成功"),
    COURSE_SAVE_SUCESS(13119, "保存成功"),
    COURSE_IS_START_ELVATION(13120, "已开始授课，测评内容只可在授课开始前进行添加"),
    COURSE_IS_START_EXAM(13121, "已开始授课，考题内容只可在授课开始前进行添加"),
    TEACHER_CANNOT_JOIN_COURSE(13122, "您是授课讲师，无法报名该课程"),
    STUDENT_IS_JOINED_COURSE(13123, "您已经报名了该课程"),
    COURSEWARE_NOT_FINISHED(13124, "请先完成课件阅读"),
    FAILED_JOIN_COURSE(13125, "报名失败"),
    COURSEWARE_NOT_READ(13126, "请先阅读课件"),
    COURSE_STARTTIME_NOT_FIT(13127, "授课时间必须大于当前时间2个小时"),
    EXAM_QUESTION_DELETE_FAIL(13128, "该题目删除失败"),
    EXAM_QUESTIONOPTION_DELETE_FAIL(13129, "该选项删除失败"),
    RESOURCE_CATEGORY_NAME_EXIST(13130, "资源类别名称已存在"),
    SIGN_IN_BEFOR_TIME(13131, "课程未开始，无法签到"),
    COURSE_NOT_BEGIN(13132, "课程未开始"),
    COURSE_SAVE_FAILED(13133, "考试题数大于题库题数"),
    COURSE_SAVE_ERROR(13134, "通过题数大于考试题数"),
    COURSE_NOT_PUBLISH(13135, "非公开课程，签到无效"),
    FAILED_JOIN_COURSE_UNAUTHORIZED(13136, "报名无效"),
    KEY_OR_CHECK_DATE_MUST_EXIST_ONE(13137, "关键字和时间必须传入其一"),
    COURSE_UPDATESTATUS_FAIL_COURSEWARE_EMPTY(13138, "课程发布失败，请上传课件"),
    NOT_SATISIED_ACHIVE_POINT(13139, "不满足获得学分条件"),
    COURSE_CREDIT_IS_NEGATIVE(13139, "课程学分不能为负数"),
    SERIES_NOT_EXIST(13140,"未查询到系列的信息"),
    SERIES_POINT_EXIST(13141,"未查询到用户系列学分记录"),
    TEACHER_TYPE_EXTERNAL_CAN_NOT_APP(13140, "外聘讲师不能使用app推送"),
    SERIES_SIGNUP_ALREADY(13142,"您已经报名了该系列"),
    STRING_LIMIT(13141,"输入文字过多，文字应限定在512字以内"),
    
    /** 考试相关 13150~13199*/
    TEST_LIB_NOT_EXIST(13150, "试题信息不存在"),
    TEST_LIB_INVALID(13151, "试题信息异常"),
    NOT_APPLY_TEST(13152, "您还没有参加过该门考试"),
    TEST_IS_PASSED(13153, "您已经通过了该门考试"),
    TEST_IS_PUBLISHED(13154, "您已参加过该门考试"),
    TESTLIB_ISUSED(13155, "该题库正在被使用"),
    TEST_CANT_RETEST(13156, "管理员已设置不可重考"),
    STUDENT_ADD_FAILED(13157, "保存考试对象失败"),
    TEST_NOT_EXIST(13157, "考试不存在"),
    TEST_IS_END(13158,"对不起，考试未发布或已经结束"),
    TEST_NOT_SELECTED(13159,"您没有报名该考试"),
    TEST_CAN_NOT_RETRY(13160,"对不起，该考试不能重考"),
    TEST_NOT_BEGIN(13161,"考试未开始"),
    TEST_NO_RECORD(13162,"没有考试记录"),
    TEST_IS_BEGIN(13163,"考试已开始，考试相关信息不可更改"),
    DURATION_CANNOT_NEGATIVE(13163,"考试时长不可为负数"),
    DRAWNUM_CAN_NOT_NEGATIVE(13164,"考试题数不可为负数"),
    PASSNUM_CAN_NOT_NEGATIVE(13165,"合格题数不可为负数"),
    
    /** 评测相关 13200~13249*/
    SUBMIT_EVAL_INVALID(13200, "您提交的评测信息有误，请重新提交"),

    /** 视频相关 13250~13299*/
    FAILED_GET_TLS_SIGN(13250, "获取视频服务失败"),
    FAILED_START_LIVE(13251, "开启直播服务失败"),
    INVALID_LIVE_ROOM_ID(13252, "无效的直播房间号"),
    FAILED_END_LIVE(13253, "结束直播失败"),
    USER_LIVE_GOING_ON(13254, "直播正在进行，无法开启其他直播"),
    ILEGAL_ACCESS_TO_LIVE(13255, "您无权限观看此直播"),
    LIVE_END(13256, "直播已结束"),
    INVALID_LIVE_ID(13257,"无效的直播id"),
    FAILED_DELETE_LIVE(13258,"删除直播失败"),
    FAILED_DELETE_LIVE_WATCH_RECORD(13259,"删除直播观看记录失败"),

    /** 系统服务（意见反馈，系统消息）相关 13300~13349*/
    FEEDBACK_OVER_LENGTH(13300, "反馈意见不能超过100个字符"),
    FEEDBACK_INVALID_CONTACT_INFO(13301, "请输入正确的email或手机号"),
    FEEDBACK_FAILED_SAVE(13302, "保存反馈意见失败"),
    SEND_SMS_TOO_MUCH(13303, "对不起，1分钟之内只能发送一条短信"),
    NOTICE_TYPE_ERROR(13304, "消息类型错误"),

    /** 用户相关 13350~13449*/
    USER_ACTIVATION_NOT_REQUIRED(13350, "用户账号无需激活"),
    USER_ACTIVATION_FAILED(13351, "用户账号激活失败"),
    USER_REJECT_ACTIVATION_NOT_REQUIRED(13352, "您的账户无法拒绝激活"),
    USER_REJECT_ACTIVATION_FAILED(13353, "拒绝激活失败"),
    USER_REGISTER_FAILED(13354, "用户注册失败"),
    SESSION_ID_EMPTY(13355, "sessionId为空"),
    USER_NAME_OVER_LENGTH(13356, "姓名不能超过20个字符"),
    INSTITUTION_OVER_LENGTH(13357, "医院机构名不能超过64个字符"),
    USER_REFUSED_VERIFY(13357, "您已拒绝激活，请联系贵医院的管理员进行处理"),
    REGISTED_SCHOOL_USER(13358, "该手机号已经注册云学院"),
    USER_INFO_NOT_VERIFILED(13359, "提交的信息与您在医链认证的信息不一致，信息提交并通过医院管理员后医链的认证信息将同步修改，确认提交？"),
    USER_APPLY_FAILED(13360, "提交申请信息失败"),
    HOSPITAL_FACULTY_NOT_MATCHED(13361, "医院科室信息不匹配，请重新选择"),
    HOSPITAL_NOT_FIND(13362, "该医院不存在"),
    WORK_NO_OVER_LENGTH(13363, "工号不能超过10个字符"),
    PROFESSIONAL_TITLE_NOT_FIND(13364, "请选择正确的职称"),
    POSITION_NOT_FIND(13365, "请选择正确的职位"),
    USER_STATE_CANNOT_APPLY(13366, "您当前的账户状态不能申请云学院，如有疑问请联系您所在医院的管理员"),
    USER_IMPORT_EXCEL_FAIL(13367, "文件数据格式不正确"),
    CASTE_NOT_FIND(13368, "请选择正确的身份"),
    EDUCATION_NOT_FIND(13368, "请选择正确的学历"),
    INSTITUTION_NO_DOMAIN(13370, "请先给医院机构设置静态域名"),



    /** 病理教学读片会相关 15000~15100*/
    PATHOLOGY_DIAGNOSIS_ACTIVITIE_DELETE_FAIL(15000, "读片会删除失败"),
    PATHOLOGY_DIAGNOSIS_ACTIVITIE_ADD_FAIL(15001, "读片会添加失败"),
    PATHOLOGY_DIAGNOSIS_ACTIVITIE_EDIT_FAIL(15004, "读片会修改失败"),
    PATHOLOGY_CASE_ACTIVITIE_DELETE_FAIL(15002, "读片删除失败"),
    PATHOLOGY_CASE_ACTIVITIE_ADD_FAIL(15003, "读片添加失败"),
    PATHOLOGY_CASE_ACTIVITIE_EDIT_FAIL(15005, "读片修改失败"),

	  /** 课件相关 13450~13549*/
    COURSEWARE_DELETE_FAIL(13448, "课件删除失败"),
    COURSEWARE_ADD_VIDEO_FAIL(13449, "添加本地视频失败"),
    COURSEWARE_GET_VIDEO_FAIL(13450, "获取课件信息失败"),
    COURSEWARE_ADD_FAIL(13549, "课件添加失败"),

    /** 进修WEB相关 18000~ */
    OVER_ORG_PERSON(18000, "申请进修科室超过可选人数上限"),
    EMPTY_COURSE(18001, "课程为空"),
    NO_RECORD(18002, "没有记录"),
    COMMON_ERROR(18003, "进修常见错误"),
    EMPTY_EMAIL(18004, "邮箱为空"),
    EMPTY_EMAIL_CODE(18005, "邮箱验证码为空"),
    EMPTY_MOBILE(18006, "手机号码为空"),
    EMPTY_PASSWORD(18007, "密码为空"),
    INVALID_EMAIL_CODE(18008, "无效的邮箱验证码"),
    PHONE_IS_BINDED(18009, "手机号已经被绑定"),
    ADVANCED_USER_EXIST(18010, "进修用户已经存在"),

    /** 继教支付 19000-20000*/
    NO_MATCH_PAY_CHANNEL(19000,"没有对应的支付渠道"),
    NO_MATCH_PAY_TYPE(19001,"没有对应的支付方式"),
    NO_MATCH_PAY_FLOW(19002,"没有对应的支付流水"),
    PREPAY_API_ERROR(19003,"调用预下单接口失败"),
    ORDER_HAS_PAYED(19004,"订单已经支付"),
    INVALID_FEE(19005, "无效的支付费用"),
    REFUND_APPLY_FAIL(19006, "退款申请失败"),
    
    /** 进修APP相关20002~20199 **/
    ERROR_EMAIL(20002, "错误的email格式"),
    ERROR_ADVANCED_TYPE(20003, "进修类型错误"),
    ERROR_APPLY_OPERATION(20004, "进修记录状态与当前操作不符"),
    ERROR_ADVANCED_APPLY(20005, "错误的进修申请记录"),
    ERROR_ADVANCED_APPLY_INFO(20006, "进修申请信息不存在或错误"),
    ERROR_ADVANCED_APPLY_ORG(20007, "进修申请科室不存在或错误"),
    ERROR_ADVANCED_APPLY_EXPERIENCE(20008, "进修申请经历不存在或错误"),
    ERROR_ADVANCED_REPORT(20009, "进修报到单不存在或错误"),
    ERROR_ADVANCED_APPLY_COST(20010, "进修申请费用不存在或错误"),
    ERROR_ADVANCED_APPLY_ACCESSORY(20011, "进修申请附件不存在或错误"),
    NOT_EXIST_ACCESSORY_TYPE(20012, "进修附件类型不存在"),
    ERROR_ADVANCED_HOSPITAL(20013, "可进修医院不存在或错误"),
    ERROR_USER_NAME_OR_PASSWORD(20014, "用户名或密码错误"),

    NOT_EXIST_INSTITUTION(20100, "机构不存在"),
    SEND_EMAIL_FAIL(20101, "邮件发送失败"),
    ADVANCED_ACCESSORY_NOT_EXISTS(20102, "进修附件不存在"),
    TICKET_INVALID(20103, "无效的抵扣券"),
    TICKET_HAS_USED(20104, "抵扣券已被使用"),
    CAN_NOT_REFUND(20105, "不可退费"),
    UN_PAYD(20106, "未完成支付"),
    
    /** 进修管理后台相关20200~ **/
    HOSPITAL_NAME_IS_BLANK(20200, "医院名称为空"),
    ERROR_IS_ADVANCED(20201, "是否可进修状态错误"),
    SERVICE_FEE_IS_NULL(20202, "进修费用为空"),
    ADVANCED_HOSPITAL_ID_IS_NULL(20203, "可进修医院id为空"),
    
    /** 规培相关21000~ **/
    EXCEL_IMPORT_IS_EMPTY(21000, "导入excel表中没有数据"),
    EXCEL_IMPORT_MISS_FIELD(21001, "excel表导入缺少必填字段"),
    UPDATE_TEACHER_ERROR(21002, "更新讲师信息失败"),
    UPDATE_ADMIN_ERROR(21003, "更新管理员信息失败"),
    UPDATE_STUDENT_ERROR(21004, "更新学员信息失败"),
    PROPERTY_COPY_ERROR(21005, "属性拷贝错误"),
    NOT_ACCESS_AUTHORITY(21006, "没有访问权限"),
    NOT_IN_FACULTY_ROTATE(21007, "没有在轮轮转记录"),
    ROLE_NOT_IN_INSTITUTION(21008,"机构不存在该角色"),
    USER_HAS_THE_ROLE(21009,"用户已经拥有该角色"),
    USER_IS_TEARCHER_OR_ADMIN(21010,"抱歉，该用户已被添加为管理员或规培讲师，不可再添加为规培学员"),
    USER_IS_STUDENT(21011,"抱歉，该用户已被添加为规培学员，不可再添加为管理员或规培讲师"),
    EXIST_OUT_FACULTY_AUDIT(21012, "已存在审批中的出科申请"),
    CAN_NOT_AUDIT(21013, "用户没有审批的权限"),
    OUT_FACULTY_HAS_AUDIT(21014, "出科申请已被审核"),
    NOT_EXIST_HOSPITAL_ORG(21015, "院内科室不存在"),
    NOT_EXIST_TEACHER(21016, "带教老师不存在");
    
    
    private Integer retCode;
    private String desc;

    ResCode(Integer code, String description) {
        this.retCode = code;
        this.desc = StringUtils.trim(description);
    }

    public Integer getRetCode() {
        return retCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

