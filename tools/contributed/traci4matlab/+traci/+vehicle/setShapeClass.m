function setShapeClass(vehID, clazz)
%setShapeClass Sets the shape class for this vehicle.
%   setShapeClass(VEHID,CLASS) Sets the shape class for this vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setShapeClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendStringCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_SHAPECLASS, vehID, clazz);