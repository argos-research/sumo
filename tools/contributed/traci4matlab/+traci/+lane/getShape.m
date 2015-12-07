function shape = getShape(laneID)
%getShape Get the shape of the lane.
%   shape = getShape(LANEID) Returns a list of 2D positions (cartesian) 
%   describing the geometry of the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
shape = traci.lane.getUniversal(constants.VAR_SHAPE, laneID);