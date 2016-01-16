/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.zuoxiaolong.niubi.job.service.impl;

import com.zuoxiaolong.niubi.job.api.data.NodeData;
import com.zuoxiaolong.niubi.job.core.helper.LoggerHelper;
import com.zuoxiaolong.niubi.job.service.NodeService;
import com.zuoxiaolong.niubi.job.service.view.NodeView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaolong Zuo
 * @since 1/15/2016 12:04
 */
@Service
public class NodeServiceImpl extends AbstractService implements NodeService {

    @Override
    public List<NodeView> getAllStandbyNodes() {
        List<NodeData> nodeModelList;
        List<NodeView> nodeViewList = new ArrayList<>();
        try {
            nodeModelList = apiFactory.nodeApi().selectAllStandbyNodes();
        } catch (Exception e) {
            LoggerHelper.warn("select all standby nodes failed, has been ignored [" + e.getClass().getName() + ", " + e.getMessage() + "]");
            return nodeViewList;
        }
        for (NodeData nodeData : nodeModelList) {
            NodeView nodeView = new NodeView();
            nodeView.setId(nodeData.getId());
            if (nodeData.getData() != null) {
                nodeView.setIp(nodeData.getData().getIp());
                nodeView.setState(nodeData.getData().getState());
                nodeView.setRunningJobCount(nodeData.getData().getRunningJobCount());
            }
            nodeViewList.add(nodeView);
        }
        return nodeViewList;
    }

}
