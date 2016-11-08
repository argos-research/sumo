function width = getWidth(laneID)
%getWidth Returns the width of the lane in m.
%   width = getWidth(LANEID) Returns the width of the lane in m.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getWidth.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
width = traci.lane.getUniversal(constants.VAR_WIDTH, laneID);