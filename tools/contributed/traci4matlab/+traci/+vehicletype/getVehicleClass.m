function vehicleClass = getVehicleClass(typeID)
%getVehicleClass Returns the class of vehicles of this type.
%   vehicleClass = getVehicleClass(TYPEID) Returns the class of vehicles of 
%   this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getVehicleClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
vehicleClass = traci.vehicletype.getUniversal(constants.VAR_VEHICLECLASS, typeID);