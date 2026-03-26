package cn.iocoder.yudao.module.opcua.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 报警状态枚举
 */
@Getter
@AllArgsConstructor
public enum AlarmStatusEnum implements ArrayValuable<Integer> {

    PENDING(0, "待处理"),
    PROCESSED(1, "已处理");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(AlarmStatusEnum::getStatus).toArray(Integer[]::new);

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
