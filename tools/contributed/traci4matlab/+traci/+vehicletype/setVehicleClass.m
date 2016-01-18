function setVehicleClass(typeID, clazz)
%setVehicleClass Sets the class for vehicles of this type.
%   setVehicleClass(TYPEID,CLASS) Sets the class for vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setVehicleClass.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
traci.sendStringCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_VEHICLECLASS, typeID, clazz);