/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeequan.jeepay.pay.mq.topic;

import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.pay.service.ConfigContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/*
* 更改商户信息
*
* @author terrfly
* @site https://www.jeepay.vip
* @date 2021/6/8 17:31
*/
@Slf4j
@Component
public class MqTopic4ModifyMchInfo{

    @Autowired private ConfigContextService configContextService;

    /** 接收 [商户配置信息] 的消息
     * 已知推送节点：
     * 1. 更新商户基本资料和状态
     * 2. 删除商户时
     * **/
    @JmsListener(destination = CS.MQ.TOPIC_MODIFY_MCH_INFO, containerFactory = "jmsListenerContainer")
    public void receive(String mchNo) {

        log.info("接收 [商户配置信息] 的消息, msg={}", mchNo);
        configContextService.initMchInfoConfigContext(mchNo);
    }

    /** 接收 [商户应用支付参数配置信息] 的消息
     * 已知推送节点：
     * 1. 更新商户应用配置
     * 2. 删除商户应用配置
     * **/
    @JmsListener(destination = CS.MQ.TOPIC_MODIFY_MCH_APP, containerFactory = "jmsListenerContainer")
    public void receiveMchApp(String mchNoAndAppId) {

        log.info("接收 [商户应用支付参数配置信息] 的消息, msg={}", mchNoAndAppId);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(mchNoAndAppId);
        configContextService.initMchAppConfigContext(jsonObject.getString("mchNo"), jsonObject.getString("appId"));
    }


}
