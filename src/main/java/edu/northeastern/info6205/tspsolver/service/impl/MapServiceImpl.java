package edu.northeastern.info6205.tspsolver.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.northeastern.info6205.tspsolver.model.Action;
import edu.northeastern.info6205.tspsolver.model.ActionType;
import edu.northeastern.info6205.tspsolver.model.Point;
import edu.northeastern.info6205.tspsolver.service.MapService;
import edu.northeastern.info6205.tspsolver.service.WebSocketPublishService;

@Service
public class MapServiceImpl implements MapService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MapServiceImpl.class);

	@Autowired
	private WebSocketPublishService webSocketPublishService;
	
	@Override
	public void publishClearMap() {
		LOGGER.debug("publishing clear map message");
		Action<Void> clearAction = new Action<>();
		clearAction.setActionType(ActionType.CLEAR_MAP);
		webSocketPublishService.publish(clearAction);
	}

	@Override
	public void publishAddPointsAndFitBound(List<Point> points) {
		LOGGER.debug("publishing add points and fit bounds for points size: {}", points.size());
		
		Action<List<Point>> action = new Action<>();
		action.setActionType(ActionType.ADD_POINT_LIST_AND_FIT_BOUND);
		action.setPayload(points);
		
		webSocketPublishService.publish(action);
	}

}