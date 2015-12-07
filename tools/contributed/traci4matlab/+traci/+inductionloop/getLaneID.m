function laneID = getLaneID(loopID)
%getLaneID Get the id of the lane the loop is on.
%   laneID = getLaneID(LOOPID) Returns the id of the lane the loop is on.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
laneID = traci.inductionloop.getUniversal(constants.VAR_LANE_ID, loopID);