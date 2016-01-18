function width = getWidth(typeID)
%getVehicleClass
%   vehicleClass = getVehicleClass(TYPEID) Returns the width in m of 
%   vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getWidth.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
width = traci.vehicletype.getUniversal(constants.VAR_WIDTH, typeID);