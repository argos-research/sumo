function length = getLength(laneID)
%getLength Returns the length of the lane in m.
%   length = getLength(LANEID) Returns the length of the lane in m.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLength.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
length = traci.lane.getUniversal(constants.VAR_LENGTH, laneID);