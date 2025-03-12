package com.pn.service.entity.notify;

import com.pn.common.enums.NotifyEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;



@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class NotifyMsgEvent<T> extends ApplicationEvent {

    private T content;

    private NotifyEnum notify;

    public NotifyMsgEvent(Object source, NotifyEnum notify, T t) {
        super(source);
        this.notify = notify;
        this.content = t;
    }

}
