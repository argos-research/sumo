function arrivedNumber = getArrivedNumber()
%getArrivedNumber Get the number of arrived vehicles.
%   arrivedNumber = getArrivedNumber() Returns the number of vehicles which
%   arrived (have reached their destination and are removed from the road 
%   network) in this time step. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getArrivedNumber.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
arrivedNumber = traci.simulation.getUniversal(constants.VAR_ARRIVED_VEHICLES_NUMBER);