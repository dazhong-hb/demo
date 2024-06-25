package com.lwz.demo.transaction.state;

/**
 * 支付状态机
 */
public enum LoanStatus implements BaseStatus {

    INIT("INIT", "初始化"),
    PAYING("PAYING", "支付中"),
    PAID("PAID", "支付成功"),
    FAILED("FAILED", "支付失败"),
    ;

    // 支付状态机内容
    private static final StateMachine<LoanStatus, LoanEvent> STATE_MACHINE = new StateMachine<>();

    static {
        // 初始状态
        STATE_MACHINE.accept(null, LoanEvent.PAY_CREATE, INIT);
        // 支付中
        STATE_MACHINE.accept(INIT, LoanEvent.PAY_PROCESS, PAYING);
        // 支付成功
        STATE_MACHINE.accept(PAYING, LoanEvent.PAY_SUCCESS, PAID);
        // 支付失败
        STATE_MACHINE.accept(PAYING, LoanEvent.PAY_FAIL, FAILED);
    }

    // 状态
    private final String status;

    // 描述
    private final String description;

    LoanStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    /**
     * 通过源状态和事件类型获取目标状态
     */
    public static LoanStatus getTargetStatus(LoanStatus sourceStatus, LoanEvent event) {
        return STATE_MACHINE.getTargetStatus(sourceStatus, event);
    }

}