function edgeID = getEdgeID(laneID)
%getEdgeID Returns the id of the edge the lane belongs to.
%   edgeID = getEdgeID(LANEID) Returns the id of the edge the lane belongs 
%   to.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
edgeID = traci.lane.getUniversal(constants.LANE_EDGE_ID, laneID);