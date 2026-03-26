package cn.iocoder.yudao.module.opcua.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 报警级别枚举
 */
@Getter
@AllArgsConstructor
public enum AlarmLevelEnum implements ArrayValuable<Integer> {

    INFO(1, "提示"),
    WARNING(2, "警告"),
    ERROR(3, "错误"),
    CRITICAL(4, "严重");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(AlarmLevelEnum::getLevel).toArray(Integer[]::new);

    /**
     * 级别
     */
    private final Integer level;
    /**
     * 级别名
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
