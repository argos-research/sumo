function setLength(laneID, len)
%setLength Sets the length of the lane in m.
%   setLength(LANEID,LENGTH) Sets the length of the lane in m.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setLength.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_LANE_VARIABLE, constants.VAR_LENGTH, laneID, len)