function allowed = getAllowed(laneID)
%getAllowed Get the allowed vehicle classes in the lane.
%   allowed = getAllowed(LANEID) Returns a cell array of strings containing
%   the allowed vehicle classes. An empty cell array means all vehicles are
%   allowed.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getAllowed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
allowed = traci.lane.getUniversal(constants.LANE_ALLOWED, laneID);