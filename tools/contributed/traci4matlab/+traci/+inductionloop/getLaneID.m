function laneID = getLaneID(loopID)
%getLaneID Get the id of the lane the loop is on.
%   laneID = getLaneID(LOOPID) Returns the id of the lane the loop is on.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLaneID.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
laneID = traci.inductionloop.getUniversal(constants.VAR_LANE_ID, loopID);