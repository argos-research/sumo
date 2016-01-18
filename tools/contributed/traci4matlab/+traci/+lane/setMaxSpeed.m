function setMaxSpeed(laneID, speed)
%setMaxSpeed Sets a new maximum allowed speed on the lane in m/s.
%   setMaxSpeed(LANEID,SPEED) Sets a new maximum allowed speed on the lane
%   in m/s.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setMaxSpeed.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_LANE_VARIABLE, constants.VAR_MAXSPEED, laneID, speed)