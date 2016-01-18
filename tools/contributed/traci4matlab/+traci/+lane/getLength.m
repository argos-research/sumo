function length = getLength(laneID)
%getLength Returns the length of the lane in m.
%   length = getLength(LANEID) Returns the length of the lane in m.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLength.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
length = traci.lane.getUniversal(constants.VAR_LENGTH, laneID);