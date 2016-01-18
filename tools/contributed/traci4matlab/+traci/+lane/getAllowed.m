function allowed = getAllowed(laneID)
%getAllowed Get the allowed vehicle classes in the lane.
%   allowed = getAllowed(LANEID) Returns a cell array of strings containing
%   the allowed vehicle classes. An empty cell array means all vehicles are
%   allowed.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getAllowed.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
allowed = traci.lane.getUniversal(constants.LANE_ALLOWED, laneID);