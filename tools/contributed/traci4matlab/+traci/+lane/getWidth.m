function width = getWidth(laneID)
%getWidth Returns the width of the lane in m.
%   width = getWidth(LANEID) Returns the width of the lane in m.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
width = traci.lane.getUniversal(constants.VAR_WIDTH, laneID);