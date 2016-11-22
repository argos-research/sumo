function disallowed = getDisallowed(laneID)
%getDisallowed Get the disallowed vehicle classes in the lane.
%   disallowed = getDisallowed(LANEID) Returns a cell array of strings containing
%   the disallowed vehicle classes.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getDisallowed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
disallowed = traci.lane.getUniversal(constants.LANE_DISALLOWED, laneID);