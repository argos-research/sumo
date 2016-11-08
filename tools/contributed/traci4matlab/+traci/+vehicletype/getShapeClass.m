function shapeClass = getShapeClass(typeID)
%getShapeClass Returns the shape class of vehicles of this type. 
%   shapeClass = getShapeClass(TYPEID) Returns the shape class of vehicles 
%   of this type. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getShapeClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
shapeClass = traci.vehicletype.getUniversal(constants.VAR_SHAPECLASS, typeID);