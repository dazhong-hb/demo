package com.lwz.demo.transaction.state;

import com.lwz.demo.common.exception.BusinessException;

/**
 * 借款订单状态转移统一入口类
 */
public class LoanTransfer {

    // 上次状态
    private LoanStatus lastStatus;

    // 当前状态
    private LoanStatus currentStatus;

    public LoanTransfer(LoanStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * 根据事件推进状态
     */
    public void transferStatusByEvent(LoanEvent event) {
        // 根据当前状态和事件，去获取目标状态
        LoanStatus targetStatus = LoanStatus.getTargetStatus(currentStatus, event);
        // 如果目标状态不为空，说明是可以推进的
        if (targetStatus != null) {
            lastStatus = currentStatus;
            currentStatus = targetStatus;
        } else {
            // 目标状态为空，说明是非法推进，进入异常处理，这里只是抛出去，由调用者去具体处理
            throw BusinessException.newInstance("借款订单状态转换失败");
        }
    }

}