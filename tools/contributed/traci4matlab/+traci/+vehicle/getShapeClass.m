function shapeClass = getShapeClass(vehID)
%getShapeClass Returns the shape class of this vehicle.
%   shapeClass = getShapeClass(VEHID) Returns the shape class of this 
%   vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getShapeClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
shapeClass = traci.vehicle.getUniversal(constants.VAR_SHAPECLASS, vehID);