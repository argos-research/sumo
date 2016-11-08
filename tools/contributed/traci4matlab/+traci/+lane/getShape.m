function shape = getShape(laneID)
%getShape Get the shape of the lane.
%   shape = getShape(LANEID) Returns a list of 2D positions (cartesian) 
%   describing the geometry of the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getShape.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
shape = traci.lane.getUniversal(constants.VAR_SHAPE, laneID);